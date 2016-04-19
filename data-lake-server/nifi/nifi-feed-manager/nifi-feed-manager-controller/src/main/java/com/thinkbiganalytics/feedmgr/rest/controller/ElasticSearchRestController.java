package com.thinkbiganalytics.feedmgr.rest.controller;

import com.thinkbiganalytics.es.ElasticSearch;
import com.thinkbiganalytics.es.SearchResult;
import com.thinkbiganalytics.rest.JerseyClientException;

import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sr186054 on 1/13/16.
 */
@Path("/v1/search")
@Component
public class ElasticSearchRestController {


    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public Response search(@QueryParam("q") String query, @QueryParam("rows") @DefaultValue("20") Integer rows, @QueryParam("start") @DefaultValue("0") Integer start) throws JerseyClientException{

        SearchResult result = ElasticSearch.getInstance().search(query,rows,start);

        return Response.ok(result).build();
    }



}