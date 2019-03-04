/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.logic;



import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *
 * @author ne.ortega
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {
    
    @Inject
    RecipeLogic recipeLogic;

    @PersistenceContext
    EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createRecipeTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        newEntity.setDescription("Esta es una descripcion de receta valida");
        newEntity.setName("Nueva Receta");
        newEntity.getIngredients().add(ingrediente);        
        RecipeEntity resp = recipeLogic.createRecipe(newEntity);
        Assert.assertNotNull(resp);
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear1() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setName("");
        prueba.setDescription("Esta es una descripcion de receta valida");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente);        
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear2() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setName(" ");
        prueba.setDescription("Esta es una descripcion de receta valida");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente);   
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear3() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setName("Este es un nombre de receta no valido: asjhdajlwdhiowqionbwqiornbwioqbr");
        prueba.setDescription("Esta es una descripcion de receta valida");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente); 
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear4() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setName("Tacos");
        prueba.setDescription("Preparacion para tacos");
        prueba.setDescription("Esta es una descripcion de receta valida");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente); 
        recipeLogic.createRecipe(prueba);
        
        RecipeEntity prueba1 = factory.manufacturePojo(RecipeEntity.class);
        prueba1.setDescription("Esta es una descripcion de receta valida");
        IngredientEntity ingrediente1 = factory.manufacturePojo(IngredientEntity.class);
        prueba1.getIngredients().add(ingrediente1); 
        prueba1.setName(prueba.getName());
        recipeLogic.createRecipe(prueba1);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear5() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setDescription("");
        prueba.setName("Tacos");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente); 
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear6() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setDescription(" ");
        prueba.setName("Tacos");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente); 
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear7() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setDescription("Hubiera estado bueno poder poner un tweet en la descripción ya que tienen longitud de caracteres similares,"
                + "luego recuerdo que twitter se actualizó y permite más, lo cual es bastante decepcionante. Supongo que con esto basta aslknklawndklanwkld"
                + "askldawklbdjkawfbjkwbqñjkrbñqwjkrbqñwjrbqñwjkrbñqwjkrñbqwrñbjqrqqwrwrqwrqñwrqñwrqwrqwrqwrqñwrqrqñqñwrbkqrqrqwñkñwrqbkwrq");
        prueba.setName("Tacos");
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        prueba.getIngredients().add(ingrediente); 
        recipeLogic.createRecipe(prueba);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void validarNegocioCrear8() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity prueba = factory.manufacturePojo(RecipeEntity.class);
        prueba.setDescription("Descripcion valida");
        prueba.setName("Tacos");
        recipeLogic.createRecipe(prueba);
    }
    
}
