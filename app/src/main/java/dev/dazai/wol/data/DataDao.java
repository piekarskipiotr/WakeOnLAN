package dev.dazai.wol.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DataDao {
    /* DEVICE QUERY */

    @Query("SELECT * FROM device_table")
    LiveData<List<Device>> getAll();

    @Query("SELECT * FROM device_table WHERE is_reachable = 0")
    LiveData<List<Device>>getNonActive();

    @Query("SELECT * FROM device_table WHERE is_reachable = 1")
    LiveData<List<Device>> getActive();

    @Query("SELECT * FROM device_table WHERE deviceId LIKE :id")
    LiveData<Device> getById(int id);

    @Query("SELECT * FROM device_table WHERE device_name LIKE :name")
    Device getByName(String name);

    @Query("SELECT * FROM device_table WHERE device_group_id == :groupId")
    LiveData<List<Device>> getDevicesByGroupId(int groupId);

    @Update
    void update(Device device);

    @Insert(onConflict = REPLACE)
    void insert(Device device);

    @Delete
    void delete(Device device);

    /* GROUP QUERY */

    @Query("SELECT * FROM group_table")
    LiveData<List<Group>> getAllGroups();

    @Query("SELECT * FROM group_table WHERE group_id == :groupId")
    LiveData<Group> getGroupById(int groupId);

    @Update
    void update(Group group);

    @Insert(onConflict = REPLACE)
    void insert(Group group);

    @Delete
    void delete(Group group);

    @Transaction
    @Query("SELECT * FROM group_table")
    LiveData<List<GroupWithDevices>> getGroupWithDevices();



}
