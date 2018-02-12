package com.reissmann.earthquake.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.reissmann.earthquake.rawDataModel.EarthquakeDataObject;

import java.util.List;

@Dao
public interface EarthquakeDao {
    @Query("Select * from ")
    List<EarthquakeDataObject> getAllEarthquakes();

}
