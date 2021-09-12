package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Catalog;

public interface CatalogDao extends BasicDao<Catalog> {
    Catalog getByMasterId(Long masterId);

    Catalog getByServiceId(Long serviceId);
}
