package com.WishApp.WishApp.services.Desire;

import com.WishApp.WishApp.http.request.DesireRequestDTO;
import com.WishApp.WishApp.http.response.DesireResponseDTO;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface IDesireService {

    DesireResponseDTO createDesire(DesireRequestDTO desireRequestDTO);

    DesireResponseDTO findDesireById(UUID id);

    Page<DesireResponseDTO> findByUserId(UUID userId, int page, int size, String sortBy);

    DesireResponseDTO updateDesire(UUID id, DesireRequestDTO desireRequestDTO);

    void deleteDesire(UUID id);

}
