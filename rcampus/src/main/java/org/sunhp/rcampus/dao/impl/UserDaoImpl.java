
package org.sunhp.rcampus.dao.impl;

import org.springframework.stereotype.Repository;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.dao.UserDao;

/**
 * @author fuyufeng
 *
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
}
