package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.model.dao.TagDao;
import by.zaitsev.dotdottask.model.entity.Tag;

/**
 * TagService is an interface implemented by a class that will access classes that implement the
 * {@link TagDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface TagService extends BaseService<Tag> {
}
