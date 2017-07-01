
package org.sunhp.rcampus.dao.impl;

import org.springframework.stereotype.Repository;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.dao.CourseDao;

/**
 * @author fuyufeng
 *
 */
@Repository("courseDao")
public class CourseDaoImpl extends BaseDaoImpl<Course> implements CourseDao {
}
