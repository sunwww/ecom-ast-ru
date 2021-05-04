package ru.ecom.mis.ejb.service.lpu;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
@Stateless
@Remote(ILpuService.class)
@Local(ILpuService.class)
public class LpuServiceBean implements ILpuService {

	
	private static final Logger LOG = Logger.getLogger(LpuServiceBean.class);

    public boolean canShowAreas(long aLpuId) {
        MisLpu lpu = manager.find(MisLpu.class, aLpuId) ;
        return lpu.getSubdivisions().isEmpty() ;
    }

    public boolean canShowSubdivisions(long aLpuId) {
        MisLpu lpu = manager.find(MisLpu.class, aLpuId) ;
        return lpu.getAreas().isEmpty() ;
    }

    
    @SuppressWarnings("unchecked")
	public void createMisLpuFromOmcLpu(String aParam) {
    	List<OmcLpu> omcLpus = manager.createQuery("from OmcLpu").getResultList() ;
    	int i =0;
    	for(OmcLpu omcLpu : omcLpus) {
    		i++ ;
    		//if(i>4) break ;
    		MisLpu lpu = QueryResultUtil.getFirst(MisLpu.class, manager.createQuery("from MisLpu where omcCode= :code and parent_id is null")
    				.setParameter("code", omcLpu.getCode()));
    		LOG.info("processing "+omcLpu.getCode()+" ("+i+"/"+omcLpus.size()+")") ;
    		if(lpu==null) {
    			lpu = new MisLpu() ;
    			lpu.setName(omcLpu.getName()) ;
    			lpu.setOmcCode(omcLpu.getCode()) ;
    			lpu.setEmail(omcLpu.getMail()) ;
    			lpu.setDirector(omcLpu.getDirector());
    			lpu.setPhone(omcLpu.getPhone());
    			try {
        			lpu.setInn(Long.parseLong(omcLpu.getInn())) ;
    			} catch (Exception e) {
    			}
    			manager.persist(lpu) ;
    			LOG.info(" New LPU created "+lpu) ;
    		}
    		if("user".equals(aParam)) {
        		createOrFindUser(lpu) ;
    		}
    	}
    }

    private SecUser createOrFindUser(MisLpu lpu) {
    	String login = "lpu"+lpu.getOmcCode() ;
    	SecUser user = QueryResultUtil.getFirst(SecUser.class
    			 , manager.createQuery("from SecUser where login=:user")
    			 .setParameter("user", login )
    			 ) ;
    	if(user==null) {
    		user = new SecUser();
    		user.setLogin(login);
    		user.setFullname(lpu.getName()) ;
    		user.setComment(lpu.getName()) ;
    		manager.persist(user) ;
    		manager.flush() ;
    		manager.clear() ;
    		manager.refresh(user);
    		//manager.refresh(user);
    	}
    	user.setPassword(generatePassword()) ;
		user.setComment(lpu.getName()+" : Администратор") ;
		
		SecRole adminRole = findAdminLpuRole() ;
		if(!user.getRoles().contains(adminRole)) {
			user.getRoles().add(findAdminLpuRole()) ;
		}
		SecRole concreteLpuRole = findContceteLpuRole(lpu) ;
		if(!user.getRoles().contains(concreteLpuRole)) {
			user.getRoles().add(concreteLpuRole) ;
		}
		
    	return user ;
	}
    
    

    private SecRole findContceteLpuRole(MisLpu lpu) {
    	String key = "lpu"+lpu.getOmcCode() ;
    	SecRole role = QueryResultUtil.getFirst(SecRole.class
    			, manager.createQuery("from SecRole where key=:key")
    			.setParameter("key", key)) ;
		SecPolicy policy = QueryResultUtil.getFirst(SecPolicy.class, manager.createQuery("from SecPolicy where key=:key")
    			.setParameter("key", lpu.getId()+""));
    	if(role==null) {
    		if(policy==null) {
    			throw new IllegalStateException("Нужно синхронихва") ;
    		}
    		role = findRoleWithPolicy(policy, lpu) ;
    	} else {
			LOG.info("finding policy ="+role.getSecPolicies()) ;
			for(SecPolicy pol : policy.getChildsSecPolicies()) {
				if(!role.getSecPolicies().contains(pol)) {
						LOG.info("addng policy policy ="+pol) ;
					role.getSecPolicies().add(pol) ;
				}
			}
    	}
    	return role ;
	}
    
    @SuppressWarnings("unchecked")
	private SecRole findRoleWithPolicy(SecPolicy aPolicy, MisLpu aLpu ) {
    	LOG.info("findRolwWithPolicy "+aPolicy) ;
    	List<SecRole> roles = manager.createQuery("from SecRole").getResultList() ;
    	for(SecRole role : roles) {
    		LOG.info("  polis = "+role.getSecPolicies()) ;
    		if(role.getSecPolicies().contains(aPolicy)) {
    			return role ;
    		}
    	}
    	SecRole role = new SecRole() ;
    	role.setKey("lpu"+aLpu.getOmcCode()) ;
    	role.setName(aLpu.getName()) ;
    	manager.persist(role) ;
    	manager.flush() ;
    	manager.clear() ;
    	LOG.info("role created "+role) ;
    	return role ;
    }

	private String generatePassword() {
    	final String ALLOWED = "qwertyupasfghkmnbvcxz123456789" ;
    	StringBuilder sb = new StringBuilder() ;
    	while(sb.length()<8) {
    		long i = Math.round(Math.random() * (ALLOWED.length()-1)) ;
    		char c = ALLOWED.charAt((int)i);
    		sb.append(c) ;
    	}
    	return sb.toString();
    }


	private SecRole findAdminLpuRole() {
		return (SecRole) manager.createQuery("from SecRole where key='adminLpu'").getSingleResult()		;
	}



	@PersistenceContext
    private EntityManager manager ;

	public void createOtherEquipment(long aLpu, long aEquipment) {
		manager.createNativeQuery("insert into equipment_mislpu (equipment_id, otherLpu_id) values ( :equipment, :lpu )")
			.setParameter("lpu", aLpu).setParameter("equipment", aEquipment).executeUpdate();
	}
	public void removeOtherEquipment(long aLpu, long aEquipment) {
		manager.createNativeQuery("delete from equipment_mislpu where equipment_id=:equipment and otherLpu_id=:lpu")
			.setParameter("lpu", aLpu).setParameter("equipment", aEquipment).executeUpdate();
	}
}
