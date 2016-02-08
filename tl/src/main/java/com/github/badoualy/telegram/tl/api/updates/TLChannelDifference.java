package com.github.badoualy.telegram.tl.api.updates;

import com.github.badoualy.telegram.tl.TLContext;
import com.github.badoualy.telegram.tl.api.TLAbsChat;
import com.github.badoualy.telegram.tl.api.TLAbsMessage;
import com.github.badoualy.telegram.tl.api.TLAbsUpdate;
import com.github.badoualy.telegram.tl.api.TLAbsUser;
import com.github.badoualy.telegram.tl.core.TLVector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.github.badoualy.telegram.tl.StreamUtils.readInt;
import static com.github.badoualy.telegram.tl.StreamUtils.readTLVector;
import static com.github.badoualy.telegram.tl.StreamUtils.writeBoolean;
import static com.github.badoualy.telegram.tl.StreamUtils.writeInt;
import static com.github.badoualy.telegram.tl.StreamUtils.writeTLVector;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_BOOLEAN;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_CONSTRUCTOR_ID;
import static com.github.badoualy.telegram.tl.TLObjectUtils.SIZE_INT32;

/**
 * @author Yannick Badoual yann.badoual@gmail.com
 * @see <a href="http://github.com/badoualy/kotlogram">http://github.com/badoualy/kotlogram</a>
 */
public class TLChannelDifference extends TLAbsChannelDifference {
    public static final int CONSTRUCTOR_ID = 0x2064674e;

    protected TLVector<? extends TLAbsMessage> newMessages;

    protected TLVector<? extends TLAbsUpdate> otherUpdates;

    protected TLVector<? extends TLAbsChat> chats;

    protected TLVector<? extends TLAbsUser> users;

    public TLChannelDifference() {
    }

    public TLChannelDifference(boolean _final, int pts, Integer timeout, TLVector<? extends TLAbsMessage> newMessages, TLVector<? extends TLAbsUpdate> otherUpdates, TLVector<? extends TLAbsChat> chats, TLVector<? extends TLAbsUser> users) {
        this._final = _final;
        this.pts = pts;
        this.timeout = timeout;
        this.newMessages = newMessages;
        this.otherUpdates = otherUpdates;
        this.chats = chats;
        this.users = users;
    }

    private void computeFlags() {
        flags = 0;
        flags = _final ? (flags | 1) : (flags &~ 1);
        flags = timeout != null ? (flags | 2) : (flags &~ 2);
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        computeFlags();

        writeInt(flags, stream);
        if ((flags & 1) != 0) writeBoolean(_final, stream);
        writeInt(pts, stream);
        if ((flags & 2) != 0) writeInt(timeout, stream);
        writeTLVector(newMessages, stream);
        writeTLVector(otherUpdates, stream);
        writeTLVector(chats, stream);
        writeTLVector(users, stream);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        flags = readInt(stream);
        _final = (flags & 1) != 0;
        pts = readInt(stream);
        timeout = (flags & 2) != 0 ? readInt(stream) : null;
        newMessages = readTLVector(stream, context);
        otherUpdates = readTLVector(stream, context);
        chats = readTLVector(stream, context);
        users = readTLVector(stream, context);
    }

    @Override
    public int computeSerializedSize() {
        computeFlags();

        int size = SIZE_CONSTRUCTOR_ID;
        size += SIZE_INT32;
        if ((flags & 1) != 0) size += SIZE_BOOLEAN;
        size += SIZE_INT32;
        if ((flags & 2) != 0) size += SIZE_INT32;
        size += newMessages.computeSerializedSize();
        size += otherUpdates.computeSerializedSize();
        size += chats.computeSerializedSize();
        size += users.computeSerializedSize();
        return size;
    }

    @Override
    public String toString() {
        return "updates.channelDifference#2064674e";
    }

    @Override
    public int getConstructorId() {
        return CONSTRUCTOR_ID;
    }

    public boolean getFinal() {
        return _final;
    }

    public void setFinal(boolean _final) {
        this._final = _final;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public TLVector<? extends TLAbsMessage> getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(TLVector<? extends TLAbsMessage> newMessages) {
        this.newMessages = newMessages;
    }

    public TLVector<? extends TLAbsUpdate> getOtherUpdates() {
        return otherUpdates;
    }

    public void setOtherUpdates(TLVector<? extends TLAbsUpdate> otherUpdates) {
        this.otherUpdates = otherUpdates;
    }

    public TLVector<? extends TLAbsChat> getChats() {
        return chats;
    }

    public void setChats(TLVector<? extends TLAbsChat> chats) {
        this.chats = chats;
    }

    public TLVector<? extends TLAbsUser> getUsers() {
        return users;
    }

    public void setUsers(TLVector<? extends TLAbsUser> users) {
        this.users = users;
    }
}
