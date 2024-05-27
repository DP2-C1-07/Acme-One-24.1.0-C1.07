
package acme.features.authenticated.notice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.notices.Notice;

@Controller
public class AuthenticatedNoticeController extends AbstractController<Authenticated, Notice> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoticeListService	listService;

	@Autowired
	protected AuthenticatedNoticeShowService	showService;

	@Autowired
	protected AuthenticatedNoticeCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
