package parking.management.dao;

import parking.management.entity.History;

import java.util.List;

public interface HistoryDao {
    void insert(History history);
    void delete(Integer historyId);
    void update(History history);
    List<History> findAll();
    History findById(Integer historyId);
    List<History> findHistoriesByParkingZoneId(Long parkingZoneId);
}
