package com.project.pjlinkserver.interfaces;


import com.project.pjlinkserver.database.ServerDatabase;
import com.project.pjlinkserver.entity.ServerStatus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ServerDao {
  @Insert
  public void insert(ServerStatus status);

  @Query("Select * from serverstatus_table where id=:id")
  public LiveData<ServerStatus> getLiveStatus(int id);

  @Query("Select * from serverstatus_table where id=:id")
  ServerStatus getStatus(int id);
  @Update
  public void updateStatus(ServerStatus status);
}
