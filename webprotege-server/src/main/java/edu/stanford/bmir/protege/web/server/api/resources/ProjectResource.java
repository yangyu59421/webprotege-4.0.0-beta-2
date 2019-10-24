package edu.stanford.bmir.protege.web.server.api.resources;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import edu.stanford.bmir.protege.web.server.access.AccessManager;
import edu.stanford.bmir.protege.web.server.api.ActionExecutor;
import edu.stanford.bmir.protege.web.server.api.ResponseUtil;
import edu.stanford.bmir.protege.web.shared.project.GetProjectDetailsAction;
import edu.stanford.bmir.protege.web.shared.project.ProjectDetails;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.user.UserId;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 15 Apr 2018
 */
public class ProjectResource {

    @Nonnull
    private final ProjectId projectId;

    @Nonnull
    private final ActionExecutor executor;

    @Nonnull
    private final AccessManager accessManager;

    @Nonnull
    private final AxiomsResourceFactory axiomsResourceFactory;

    @Nonnull
    private final RevisionsResourceFactory changesResourceFactory;

    @SuppressWarnings("UnnecessaryFullyQualifiedName")
    @AutoFactory
    @Inject
    public ProjectResource(@Nonnull ProjectId projectId,
                           @Provided @Nonnull ActionExecutor executor,
                           @Provided @Nonnull AccessManager accessManager,
                           @Provided @Nonnull AxiomsResourceFactory axiomsResourceFactory,
                           @Provided @Nonnull RevisionsResourceFactory changesResourceFactory) {
        this.projectId = checkNotNull(projectId);
        this.executor = checkNotNull(executor);
        this.accessManager = checkNotNull(accessManager);
        this.axiomsResourceFactory = checkNotNull(axiomsResourceFactory);
        this.changesResourceFactory = checkNotNull(changesResourceFactory);
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/")
    public Response getProjectDetails(@Context UserId userId, @Context UriInfo uriInfo) {
        ProjectDetails projectDetails = executor.execute(new GetProjectDetailsAction(projectId), userId)
                                                .getProjectDetails();
        return Response.ok(projectDetails).build();
    }

    @Path("revisions")
    public RevisionsResource getRevisions() {
        return changesResourceFactory.create(projectId);
    }

    @Path("axioms")
    public AxiomsResource getProjectAxiomsResource() {
        return axiomsResourceFactory.create(projectId);
    }
}
