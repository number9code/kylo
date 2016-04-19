package com.thinkbiganalytics.feedmgr.rest.controller;

import com.thinkbiganalytics.feedmgr.InvalidOperationException;
import com.thinkbiganalytics.feedmgr.rest.model.FeedCategory;
import com.thinkbiganalytics.feedmgr.rest.model.FeedSummary;
import com.thinkbiganalytics.feedmgr.service.MetadataService;
import com.thinkbiganalytics.rest.JerseyClientException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sr186054 on 1/13/16.
 */
@Path("/v1/categories")
@Component
public class FeedCategoryRestController {
    private static final Logger LOG = Logger.getLogger(FeedCategoryRestController.class);

    @Autowired
    MetadataService metadataService;

    public FeedCategoryRestController() {
    }

    private MetadataService getMetadataService(){
        return metadataService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response getCategories() throws JerseyClientException{
        Collection<FeedCategory> categories = getMetadataService().getCategories();
       return Response.ok(categories).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON })
    public Response saveCategory(FeedCategory feedCategory) throws JerseyClientException{
        getMetadataService().saveCategory(feedCategory);
        return Response.ok(feedCategory).build();
    }

    @DELETE
    @Path("/{categoryId}")
    @Produces({MediaType.APPLICATION_JSON })
    public Response deleteCategory(@PathParam("categoryId")Long categoryId) throws JerseyClientException,
                                                                                   InvalidOperationException {
        getMetadataService().deleteCategory(categoryId);
        return Response.ok().build();
    }

    @GET
    @Path("/{categoryId}/feeds")
    @Produces({MediaType.APPLICATION_JSON })
    public Response getCategory(@PathParam("categoryId")Long categoryId) throws JerseyClientException{
        List<FeedSummary> summaryList  = getMetadataService().getFeedSummaryForCategory(categoryId);
        return Response.ok(summaryList).build();
    }



}