/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import kotlin.js.dom.html.HTMLDivElement
import kotlin.js.dom.html.document

/**
 * Created by Semyon.Atamas on 3/31/2015.
 */

class NavBarView(private val navigationElement: HTMLDivElement) {
    fun onFileSelected(file: File) {
        navigationElement.innerHTML = "";
        navigationElement.appendChild(createNavItem(file.name));
        createNavItem(file.project);
    }

    fun onProjectSelected(project: Project){
        navigationElement.innerHTML = "";
        createNavItem(project);
    }

    fun onSelectedFileDeleted(){
        navigationElement.removeChild(navigationElement.lastChild);
    }

    fun onSelectedProjectRenamed(newName: String){
        var navItem = navigationElement.childNodes.item(1) as HTMLDivElement
        navItem.textContent = newName
    }

    fun onSelectedFileRenamed(newName: String){
        var navItem = navigationElement.lastChild as HTMLDivElement
        navItem.textContent = newName
    }

    private fun createNavItem(project: Project){
        navigationElement.insertBefore(createNavItem(project.getName()), navigationElement.firstChild)
        var folder: FolderView? = project.getParent()
        while (folder != null){
            navigationElement.insertBefore(createNavItem(folder!!.name), navigationElement.firstChild)
            folder = folder!!.parent
        }
    }

    private fun createNavItem(name: String): HTMLDivElement {
        val navItem = document.createElement("div") as HTMLDivElement;
        navItem.className = "grid-nav-item";
        navItem.textContent = name;
        return navItem
    }
}