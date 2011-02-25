/**
 * Copyright (C) 2010 Tomasz Chodakowski <*tch (at) wp.eu*>
 *
 * This file is part of Project Masuria.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.seemantica.masuria.core.database;

import org.seemantica.masuria.core.IMasterLevelComponent;
import org.seemantica.masuria.core.datamodel.IElement;
import org.seemantica.masuria.core.datamodel.IElementId;
import org.seemantica.masuria.core.registry.IDescriptor;


public interface IMasterDatabase<E extends IElement> extends IDatabase<E>, IMasterLevelComponent {

	long getNumberOfElements(IDescriptor partition);
	
	Iterable<E> getElements(IDescriptor partition);
	
	Iterable<IElementId> getElementIds(IDescriptor partition);
	
	E getElement(IElementId id, IDescriptor partition);
}
