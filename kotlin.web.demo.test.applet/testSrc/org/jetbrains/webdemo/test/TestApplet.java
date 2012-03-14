/*
 * Copyright 2000-2012 JetBrains s.r.o.
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

package org.jetbrains.webdemo.test;

import junit.framework.TestCase;
import org.jetbrains.webdemo.*;
import org.jetbrains.webdemo.server.ApplicationSettings;
import org.jetbrains.webdemo.session.SessionInfo;
import org.jetbrains.webdemo.translator.WebDemoConfigApplet;
import org.jetbrains.webdemo.translator.WebDemoTranslatorFacade;
import sun.applet.AppletSecurity;

import java.util.Random;

//import org.jetbrains.webdemo.translator.WebDemoConfigApplet;

/**
 * @author Natalia.Ukhorskaya
 */

public class TestApplet extends TestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();
        InitializerApplet.getInstance().initJavaCoreEnvironment();
        ErrorWriter.ERROR_WRITER = ErrorWriterInApplet.getInstance();
        Initializer.INITIALIZER = InitializerApplet.getInstance();
        WebDemoTranslatorFacade.LOAD_JS_LIBRARY_CONFIG = new WebDemoConfigApplet(Initializer.INITIALIZER.getEnvironment().getProject());
        ApplicationSettings.IS_TEST_VERSION = "true";
        MainApplet.SESSION_INFO = new SessionInfo("applet" + new Random().nextInt());
    }


    /* Get highlighting under security manager
     */
    public void testSecurityManager() {
        assertEquals("[]", new MainApplet().getHighlighting("fun main(args : Array<String>) { }", "java"));
        System.setSecurityManager(new AppletSecurity());
        String result = new MainApplet().getHighlighting("fun main(args : Array<String>) { }", "java");
        assertEquals("[]", result);
    }

}
