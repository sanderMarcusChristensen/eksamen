package dat.dao;

import dat.dto.TripDTO;

import java.util.List;
import java.util.Set;

public interface ITripGuideDAO {
    void addGuideToTrip(int tripId, int guideId);
    List<TripDTO> getTripsByGuide(int guideId);


}
