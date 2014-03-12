package main;

import java.util.List;

import model.Document;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import dao.IDocumentDAO;
import dao.impl.DocumentDAOImpl;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"F:/Workspaces 10/Project2/WebRoot/WEB-INF/applicationContext.xml") ;
		IDocumentDAO dao = (DocumentDAOImpl)ctx.getBean("DocumentDAO") ;
//		dao.findById("402881fc43346c83014334735db10001") ;
		List<Document> documents = 
				dao.findhomeworksByInformationAndTopic("8a8a1f79431d2e0801431d3117810001", "11计算机1班");
		for(Document document : documents){
			System.out.println(document.getUser().getIdNum());
		}
	}
}	
