package Global.Holiday.Global.Holiday.enkreateclient.repositories;

import Global.Holiday.Global.Holiday.enkreateclient.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface HolidayRepository extends JpaRepository<Holiday,Long> {
    List<Holiday> findAll();

    List<Holiday> findByCountryIdAndYearId(Long countryId, Long yearId);

    boolean existsByCountryIdAndYearIdAndDateIdAndHolidayNameIgnoreCase(
            Long countryId,
            Long yearId,
            Long dateId,
            String holidayName
    );


    @Query("SELECT COUNT(h) > 0 FROM Holiday h WHERE " +
            "h.countryId = :countryId AND " +
            "h.yearId = :yearId AND " +
            "h.dateId = :dateId AND " +
            "LOWER(REPLACE(h.holidayName, ' ', '')) = :normalizedName AND " +
            "h.holidayId <> :excludeId")
    boolean existsDuplicateOnUpdate(
            @Param("countryId") Long countryId,
            @Param("yearId") Long yearId,
            @Param("dateId") Long dateId,
            @Param("normalizedName") String normalizedName,
            @Param("excludeId") Long excludeId
    );

    Optional<Holiday> findByHolidayIdAndCountryIdAndYearId(
            Long holidayId,
            Long countryId,
            Long yearId
    );

    List<Holiday> findByHolidayIdInAndCountryIdAndYearId(Set<Long> holidayIds, Long countryId, Long yearId);

    List<Holiday> findByCountryIdAndYearIdAndDateId(Long countryId, Long yearId, Long targetDateId);
}
