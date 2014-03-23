package dao.impl;

import java.util.List;

import model.CompressFile;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import dao.AbstractCompressFileDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Compressfile entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.impl.Compressfile
 * @author MyEclipse Persistence Tools
 */

public class CompressFileDAOImpl extends AbstractCompressFileDAO {
	private static final Logger log = Logger
			.getLogger(CompressFileDAOImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String SOURCE_FILE = "sourceFile";
	public static final String UNCOMPRESS_FILE_PATH = "uncompressFilePath";

	protected void initDao() {
		// do nothing
	}

	public void save(CompressFile transientInstance) {
		log.debug("saving CompressFile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompressFile persistentInstance) {
		log.debug("deleting CompressFile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompressFile findById(java.io.Serializable id) {
		log.debug("getting CompressFile instance with id: " + id);
		try {
			CompressFile instance = (CompressFile) getHibernateTemplate().get(
					CompressFile.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<?> findByExample(CompressFile instance) {
		log.debug("finding CompressFile instance by example");
		try {
			List<?> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<?> findByProperty(String propertyName, Object value) {
		log.debug("finding CompressFile instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompressFile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<?> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<?> findBySourceFile(Object sourceFile) {
		return findByProperty(SOURCE_FILE, sourceFile);
	}

	public List<?> findByUnCompressFilePath(Object unCompressFilePath) {
		return findByProperty(UNCOMPRESS_FILE_PATH, unCompressFilePath);
	}

	public List<?> findAll() {
		log.debug("finding all CompressFile instances");
		try {
			String queryString = "from CompressFile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompressFile merge(CompressFile detachedInstance) {
		log.debug("merging CompressFile instance");
		try {
			CompressFile result = (CompressFile) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompressFile instance) {
		log.debug("attaching dirty CompressFile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompressFile instance) {
		log.debug("attaching clean CompressFile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompressFileDAOImpl getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompressFileDAOImpl) ctx.getBean("CompressFileDAO");
	}
}