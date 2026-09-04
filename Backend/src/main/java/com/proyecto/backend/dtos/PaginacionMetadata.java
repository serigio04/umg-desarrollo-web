package com.proyecto.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginacionMetadata {
    private long totalRecords;
    private int page;
    private int pageSize;
    private int totalPages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
}