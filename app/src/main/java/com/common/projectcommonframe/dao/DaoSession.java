package com.common.projectcommonframe.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.common.projectcommonframe.test.TestGreenDao;

import com.common.projectcommonframe.dao.TestGreenDaoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig testGreenDaoDaoConfig;

    private final TestGreenDaoDao testGreenDaoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        testGreenDaoDaoConfig = daoConfigMap.get(TestGreenDaoDao.class).clone();
        testGreenDaoDaoConfig.initIdentityScope(type);

        testGreenDaoDao = new TestGreenDaoDao(testGreenDaoDaoConfig, this);

        registerDao(TestGreenDao.class, testGreenDaoDao);
    }
    
    public void clear() {
        testGreenDaoDaoConfig.clearIdentityScope();
    }

    public TestGreenDaoDao getTestGreenDaoDao() {
        return testGreenDaoDao;
    }

}
