package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.MasterService;

public interface MasterServiceDao extends BasicDao<MasterService> {
    MasterService getByMasterId(Long masterId);

    MasterService getByServiceId(Long serviceId);
}
