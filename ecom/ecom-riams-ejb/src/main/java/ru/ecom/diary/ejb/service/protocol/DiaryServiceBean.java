package ru.ecom.diary.ejb.service.protocol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterGroup;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.diary.ejb.form.DiaryForm;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByGroup;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByParameter;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.medcase.MedService;

/**
 * User: STkacheva
 * Протокол
 */
@Stateless
@Remote(IDiaryService.class)
public class DiaryServiceBean implements IDiaryService {
 public List<DiaryForm> findProtocol(long aSloId) {
        List<Diary> list = theManager.createQuery(
                "from Diary where sloId = :sloId"
        ).setParameter("sloId",aSloId).setMaxResults(50).getResultList();
        List<DiaryForm> ret = new LinkedList<DiaryForm>();
        for (Diary diary : list) {
            try {
                ret.add(theEntityFormService.loadForm(DiaryForm.class,  diary)) ;
            } catch (EntityFormException e) {
                throw new IllegalStateException(e) ;
            }
        }
        return ret ;
    }

	public CheckNode loadParametersByMedService(long aTemplateId) {
		TreeSet<Long> parametersSet = new TreeSet<Long>() ;
		TemplateProtocol template = theManager.find(TemplateProtocol.class, aTemplateId) ;
		for (Parameter param: template.getParameters()) {
			parametersSet.add(param.getId()) ;
		}
		
		List<ParameterGroup> rootGroups = findRootGroups() ;
		
		CheckNodeByGroup rootNode = new CheckNodeByGroup("g"+0, "/",false);
		System.out.println("-G-"+rootNode.getName()) ;
		for (ParameterGroup group : rootGroups) {
			System.out.println("-ROOT--"+group.getId()+"-"+group.getName()) ;
			CheckNodeByGroup node = new CheckNodeByGroup("g"+group.getId(),group.getName(),false) ;
			rootNode.getChilds().add(node);
			addGroups(group,node,parametersSet) ;
			addParameters(group,node,parametersSet);
		}
		
		return rootNode;
	}
	private List<ParameterGroup> findRootGroups() {
		return theManager.createQuery("from ParameterGroup where parent is null") .setMaxResults(100).getResultList();
	}
	private void addGroups (ParameterGroup aGroups, CheckNode aNode, TreeSet<Long> aParameters) {
		for (ParameterGroup group: aGroups.getChildGroups()) {
			CheckNodeByGroup node = new CheckNodeByGroup(
					"g"+group.getId() , new StringBuilder().append(group.getName()).toString(),false
					) ;
			aNode.getChilds().add(node) ;
			System.out.println("-GR-"+aNode.getId()+"-"+node.getName()) ;
			addGroups(group,node ,aParameters) ;
			addParameters(group,node,aParameters);
		}
	}
	private void addParameters(ParameterGroup aGroup, CheckNode aNode, TreeSet<Long> aParameters) {
		for (Parameter param: aGroup.getParameters()) {
			CheckNodeByParameter node = new CheckNodeByParameter("p"+param.getId(),
					new StringBuilder().append(param.getName()).append("-").append(param.getType()).toString()
					,aParameters.contains(param.getId()),param.getType());
			System.out.println("-PAR-"+aNode.getId()+"-"+node.getName()) ;
			aNode.getChilds().add(node) ;
		}
	}
	
	public void saveParametersByTemplateProtocol(long aProtocolId, long[] aAdded, long[] aRemoved) {
		TemplateProtocol protocol = theManager.find(TemplateProtocol.class, aProtocolId) ;
		//theManager.refresh(protocol) ;
		List<Parameter> params = protocol.getParameters() ;
		if (params==null) params = new LinkedList<Parameter>() ;
		System.out.println("ADDING");
		for (long idParam:aAdded) {
			System.out.println("adding id "+idParam);
			Parameter param = theManager.find(Parameter.class, idParam) ;
			if(param!=null &&!params.contains(param)) {
				params.add(param) ;
			}
			System.out.println("OK");
		}
		System.out.println("REMOVED");
		for(long idParam:aRemoved) {
			System.out.println("removed id "+idParam);
			Parameter param = theManager.find(Parameter.class, idParam) ;
			if(param!=null &&params.contains(param)) {
				params.remove(param) ;
			}
			System.out.println("OK");
		}
		System.out.println("Size params="+params.size());
		protocol.setParameters(params);
		theManager.persist(protocol) ;
		//theManager.refresh(protocol) ;
		System.out.println("Size params after saving="+protocol.getParameters().size());
		
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
}
