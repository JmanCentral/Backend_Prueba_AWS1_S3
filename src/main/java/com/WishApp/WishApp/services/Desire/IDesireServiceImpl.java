package com.WishApp.WishApp.services.Desire;

import com.WishApp.WishApp.entities.Category;
import com.WishApp.WishApp.entities.Desire;
import com.WishApp.WishApp.entities.User;
import com.WishApp.WishApp.http.request.DesireRequestDTO;
import com.WishApp.WishApp.http.response.DesireResponseDTO;
import com.WishApp.WishApp.mappers.interfacesDesire.DesireMapper;
import com.WishApp.WishApp.persistencie.CategoryRepository;
import com.WishApp.WishApp.persistencie.DesireRepository;
import com.WishApp.WishApp.persistencie.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IDesireServiceImpl implements IDesireService {

    private final DesireRepository desireRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DesireMapper desireMapper;

    @Override
    public DesireResponseDTO createDesire(DesireRequestDTO desireRequestDTO) {

        User user = userRepository.findById(desireRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Category category = categoryRepository.findById(desireRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        Desire desire = desireMapper.toEntity(desireRequestDTO);

        desire.setUser(user);
        desire.setCategory(category);
        Desire savedDesire = desireRepository.save(desire);

        System.out.println(savedDesire);

        return desireMapper.toDTO(savedDesire);

    }

    @Override
    public DesireResponseDTO findDesireById(UUID id) {
        return null;
    }

    @Override
    public Page<DesireResponseDTO> findByUserId(UUID userId , int page, int size, String sortBy) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Page<Desire> desiresPage = desireRepository.findByUserId(user.getId(), PageRequest.of(page, size, Sort.by(sortBy)));

        return desiresPage.map(desireMapper::toDTO);
    }

    @Override
    public DesireResponseDTO updateDesire(UUID id, DesireRequestDTO desireRequestDTO) {

        Desire existingDesire = desireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deseo no encontrado"));

        User user = userRepository.findById(desireRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Category category = categoryRepository.findById(desireRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        existingDesire.setTitle(desireRequestDTO.getTitle());
        existingDesire.setDescription(desireRequestDTO.getDescription());
        existingDesire.setComplianceDate(desireRequestDTO.getComplianceDate());
        existingDesire.setUser(user);
        existingDesire.setCategory(category);

        Desire updatedDesire = desireRepository.save(existingDesire);

        return desireMapper.toDTO(updatedDesire);

    }

    @Override
    public void deleteDesire(UUID id) {

        Desire existingDesire = desireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deseo no encontrado"));

        desireRepository.delete(existingDesire);
    }
}
