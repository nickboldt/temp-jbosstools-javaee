package org.domain.SeamWebWarTestProject.session;

import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name("statefulComponentWithoutRemove")
@Stateful
@Scope(ScopeType.APPLICATION)
public class StatefulComponentWithoutRemove {

	private String abc;
	
    @Logger private Log log;
	
    
    //seam-gen method
    public String statefulComponent()
    {
        //implement your business logic here
        log.info("statefulComponent.statefulComponent() action called");
        return "success";
    }
	
    @Destroy
	public void destroyMethod(){
		
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}
    
}