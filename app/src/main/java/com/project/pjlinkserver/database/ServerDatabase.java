package com.project.pjlinkserver.database;

import android.app.Application;
import android.content.Context;

import com.project.pjlinkserver.constant.Status;
import com.project.pjlinkserver.entity.ServerStatus;
import com.project.pjlinkserver.interfaces.ServerDao;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {ServerStatus.class},version = 1)
public abstract class ServerDatabase extends RoomDatabase {
  public abstract ServerDao serverDao();
  public static ServerDatabase mServerDatabase;
  public static ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
  public static synchronized ServerDatabase getInstance(Context context)
  {
      if(mServerDatabase == null)
      {
        mServerDatabase = Room.databaseBuilder(context.getApplicationContext(),ServerDatabase.class,"ServerDatabase").addCallback(initCallback).build();
      }

      return mServerDatabase;
  }

  private static RoomDatabase.Callback initCallback = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
      ServerDatabase.mExecutorService.execute(()->{
          if(mServerDatabase !=null)
          {
            ServerStatus status = new ServerStatus(1,false,
                "PJLINKSERVER",
                Status.OK,
                Status.POWER_STANDBY,
                Status.OK,
                Status.INPUT_RGB,
                Status.OK,
                Status.MUTE_VIDEO,
                Status.FAN_ERROR,
                Status.LAMP_ERROR,
                Status.TEMP_ERROR,
                Status.COVER_ERROR,
                Status.FILTER_ERROR,
                Status.OTHER_ERROR);
            mServerDatabase.serverDao().insert(status);
          }

      });
    }
  };
}
