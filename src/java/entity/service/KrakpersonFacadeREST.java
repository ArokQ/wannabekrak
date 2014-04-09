package entity.service;

import entity.CustomerRequest;
import entity.Krakperson;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Mads
 */
@Stateless
@Path("person")
@DeclareRoles({"krak", "krak-customer"})
@RolesAllowed({"krak"})
public class KrakpersonFacadeREST extends AbstractFacade<Krakperson> {
    @PersistenceContext(unitName = "WannabeKrakPU")
    private EntityManager em;

    public KrakpersonFacadeREST() {
        super(Krakperson.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Krakperson entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Krakperson entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @Resource
    SessionContext ctx;
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    @RolesAllowed({"krak-customer", "krak"})
    public Krakperson find(@PathParam("id") String id) {
        CustomerRequest customer = em.find(CustomerRequest.class, ctx.getCallerPrincipal());
        customer.update();
        em.merge(customer);
        return em.find(Krakperson.class, id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Krakperson> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Krakperson> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
