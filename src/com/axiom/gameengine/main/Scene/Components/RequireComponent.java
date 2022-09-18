/**
 * 
 */
package com.axiom.gameengine.main.Scene.Components;

import java.lang.annotation.*;

import com.axiom.gameengine.main.Scene.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * @author justi
 *
 */
public @interface RequireComponent {
	Class<? extends Component>[] classes();
}
