/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.ejb;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author CesarF
 */
@Stateless
public class RecipeLogic {
    @Inject
    private RecipePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public RecipeEntity getRecipe(Long id) {
        return persistence.find(id);
    }

    //TODO crear el método createRecipe
    public RecipeEntity createRecipe(RecipeEntity entity)throws BusinessLogicException{
        if(entity.getName().equals("") || entity.getName().equals(" ")){
            throw new BusinessLogicException("El nombre de la receta no puede ser vacío o nulo");
        } 
        if(entity.getName().length() >= 30){
            throw new BusinessLogicException("El nombre de la receta no puede superar los 30 caractereces");
        }
        if(!(persistence.findByName(entity.getName())==null)){
            throw new BusinessLogicException("No pueden existir dos recetas con el mismo nombre");
        }
        if(entity.getDescription().equals("") || entity.getDescription().equals(" ")){
            throw new BusinessLogicException("La descripción de la receta no puede ser vacía o nula");
        }
        if(entity.getDescription().length() >= 150){
            throw new BusinessLogicException("El nombre de la receta no puede superar los 150 caractereces");
        }
        if(entity.getIngredients().isEmpty()){
            throw new BusinessLogicException("Toda receta debe tener al menos un ingrediente");
        }
        return entity;
    }


}
