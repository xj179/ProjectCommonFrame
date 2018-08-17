package com.common.projectcommonframe.test;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * DaoMaster和DaoSession要编译写一个测试类才会生成，例如像下面这样
 */
@Entity
public class TestGreenDao {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;

    @Generated(hash = 1680607911)
    public TestGreenDao(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1269078430)
    public TestGreenDao() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
