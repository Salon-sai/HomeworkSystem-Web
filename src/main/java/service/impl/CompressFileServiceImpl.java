/**
 * 
 */
package service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.CompressFile;
import service.ICompressFileService;

import commom.AbstractTemplateService;
import dao.ICompressFileDAO;

/**
 * @author sai
 *
 */
@Repository("compressFileService")
public class CompressFileServiceImpl extends
		AbstractTemplateService<ICompressFileDAO, CompressFile> implements
		ICompressFileService {

	@Override
	@Resource(name="CompressFileDAO")
	public void setDao(ICompressFileDAO dao) {
		// TODO Auto-generated method stub
		this.dao = dao ;
	}
	
}
