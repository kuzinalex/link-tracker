/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records;

import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Databasechangeloglock;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatabasechangeloglockRecord extends UpdatableRecordImpl<DatabasechangeloglockRecord> implements Record4<Integer, Boolean, LocalDateTime, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.databasechangeloglock.id</code>.
     */
    public void setId(@NotNull Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.databasechangeloglock.id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.databasechangeloglock.locked</code>.
     */
    public void setLocked(@NotNull Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.databasechangeloglock.locked</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Boolean getLocked() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    public void setLockgranted(@Nullable LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    @Nullable
    public LocalDateTime getLockgranted() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockedby</code>.
     */
    public void setLockedby(@Nullable String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockedby</code>.
     */
    @Size(max = 255)
    @Nullable
    public String getLockedby() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Integer, Boolean, LocalDateTime, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<Integer, Boolean, LocalDateTime, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Integer> field1() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.ID;
    }

    @Override
    @NotNull
    public Field<Boolean> field2() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKED;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field3() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKGRANTED;
    }

    @Override
    @NotNull
    public Field<String> field4() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKEDBY;
    }

    @Override
    @NotNull
    public Integer component1() {
        return getId();
    }

    @Override
    @NotNull
    public Boolean component2() {
        return getLocked();
    }

    @Override
    @Nullable
    public LocalDateTime component3() {
        return getLockgranted();
    }

    @Override
    @Nullable
    public String component4() {
        return getLockedby();
    }

    @Override
    @NotNull
    public Integer value1() {
        return getId();
    }

    @Override
    @NotNull
    public Boolean value2() {
        return getLocked();
    }

    @Override
    @Nullable
    public LocalDateTime value3() {
        return getLockgranted();
    }

    @Override
    @Nullable
    public String value4() {
        return getLockedby();
    }

    @Override
    @NotNull
    public DatabasechangeloglockRecord value1(@NotNull Integer value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public DatabasechangeloglockRecord value2(@NotNull Boolean value) {
        setLocked(value);
        return this;
    }

    @Override
    @NotNull
    public DatabasechangeloglockRecord value3(@Nullable LocalDateTime value) {
        setLockgranted(value);
        return this;
    }

    @Override
    @NotNull
    public DatabasechangeloglockRecord value4(@Nullable String value) {
        setLockedby(value);
        return this;
    }

    @Override
    @NotNull
    public DatabasechangeloglockRecord values(@NotNull Integer value1, @NotNull Boolean value2, @Nullable LocalDateTime value3, @Nullable String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DatabasechangeloglockRecord
     */
    public DatabasechangeloglockRecord() {
        super(Databasechangeloglock.DATABASECHANGELOGLOCK);
    }

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    @ConstructorProperties({ "id", "locked", "lockgranted", "lockedby" })
    public DatabasechangeloglockRecord(@NotNull Integer id, @NotNull Boolean locked, @Nullable LocalDateTime lockgranted, @Nullable String lockedby) {
        super(Databasechangeloglock.DATABASECHANGELOGLOCK);

        setId(id);
        setLocked(locked);
        setLockgranted(lockgranted);
        setLockedby(lockedby);
    }
}
