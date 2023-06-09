/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.entity.jooq;

import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Databasechangeloglock;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Subscription;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.DatabasechangeloglockRecord;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.SubscriptionRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatRecord> CHAT_PKEY = Internal.createUniqueKey(Chat.CHAT, DSL.name("chat_pkey"), new TableField[] { Chat.CHAT.ID }, true);
    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<LinkRecord> LINK_PKEY = Internal.createUniqueKey(Link.LINK, DSL.name("link_pkey"), new TableField[] { Link.LINK.ID }, true);
    public static final UniqueKey<SubscriptionRecord> SUBSCRIPTION_PKEY = Internal.createUniqueKey(Subscription.SUBSCRIPTION, DSL.name("subscription_pkey"), new TableField[] { Subscription.SUBSCRIPTION.CHAT_ID, Subscription.SUBSCRIPTION.LINK_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<SubscriptionRecord, ChatRecord> SUBSCRIPTION__SUBSCRIPTION_CHAT_ID_FKEY = Internal.createForeignKey(Subscription.SUBSCRIPTION, DSL.name("subscription_chat_id_fkey"), new TableField[] { Subscription.SUBSCRIPTION.CHAT_ID }, Keys.CHAT_PKEY, new TableField[] { Chat.CHAT.ID }, true);
    public static final ForeignKey<SubscriptionRecord, LinkRecord> SUBSCRIPTION__SUBSCRIPTION_LINK_ID_FKEY = Internal.createForeignKey(Subscription.SUBSCRIPTION, DSL.name("subscription_link_id_fkey"), new TableField[] { Subscription.SUBSCRIPTION.LINK_ID }, Keys.LINK_PKEY, new TableField[] { Link.LINK.ID }, true);
}
