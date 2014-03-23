package dao.impl;

import java.util.List;

import model.Document;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import dao.AbstractDocumentDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Document entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see dao.impl.Document
 * @author MyEclipse Persistence Tools
 */

public class DocumentDAOImpl extends AbstractDocumentDAO {
	private static final Logger log = Logger
			.getLogger(DocumentDAOImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String SOURCE_FILE = "sourceFile";
	public static final String SWF_PATH = "swfPath";
	public static final String PDF_PATH = "pdfPath";

	protected void initDao() {
		// do nothing
	}

	public void save(Document transientInstance) {
		log.debug("saving Document instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Document persistentInstance) {
		log.debug("deleting Document instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Document findById(java.io.Serializable id) {
		log.debug("getting Document instance with id: " + id);
		try {
			Document instance = (Document) getHibernateTemplate().get(
					Document.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<?> findByExample(Document instance) {
		log.debug("finding Document instance by example");
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
		log.debug("finding Document instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Document as model where model."
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

	public List<?> findBySwfPath(Object swfPath) {
		return findByProperty(SWF_PATH, swfPath);
	}

	public List<?> findByPdfPath(Object pdfPath) {
		return findByProperty(PDF_PATH, pdfPath);
	}

	public List<?> findAll() {
		log.debug("finding all Document instances");
		try {
			String queryString = "from Document";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Document merge(Document detachedInstance) {
		log.debug("merging Document instance");
		try {
			Document result = (Document) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Document instance) {
		log.debug("attaching dirty Document instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Document instance) {
		log.debug("attaching clean Document instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DocumentDAOImpl getFromApplicationContext(ApplicationContext ctx) {
		return (DocumentDAOImpl) ctx.getBean("DocumentDAO");
	}
}