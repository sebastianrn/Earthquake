package com.reissmann.earthquake.database;


import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

import com.reissmann.earthquake.rawDataModel.EarthquakeDataObject;

@Database(entities = {EarthquakeDataObject.class}, version = 1)
public abstract class EarthquakeDatabase extends RoomDatabase {
    public abstract EarthquakeDao earthquakeDao();
}
