
package acme.features.any.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Controller
public class AnyProjectController extends AbstractController<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectListService	listService;

	@Autowired
	private AnyProjectShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
