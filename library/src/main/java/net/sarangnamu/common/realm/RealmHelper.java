/*
 * Copyright 2016. Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sarangnamu.common.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2016. 6. 3.. <p/>
 */
public class RealmHelper {
    public static final String ID = "id";

    protected RealmConfiguration mRealmConfig;

    public Realm get(Context context) {
        if (context == null) {
            return null;
        }

        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration.Builder(context).build();
        }

        return Realm.getInstance(mRealmConfig);
    }

    public void putData(Context context, RealmObject data) {
        Realm db = get(context);

        db.beginTransaction();
        db.copyFromRealm(data);
        db.commitTransaction();
    }

    public void closeRealm(Context context) {
        get(context).close();
    }

    public static long newId(Realm realm, Class<? extends RealmObject> clazz) {
        return newIdWithIdName(realm, clazz, ID);
    }

    public static long newIdWithIdName(Realm realm, Class<? extends RealmObject> clazz, String idName) {
        try {
            return realm.where(clazz).max(idName).longValue() + 1;
        } catch (NullPointerException e) {
            return 1;
        }
    }
}
