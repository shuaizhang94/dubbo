/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.compiler.support;

import org.apache.dubbo.common.compiler.Compiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compiler抽象类
 */
public abstract class AbstractCompiler implements Compiler {

    //正则 包名
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z][$_a-zA-Z0-9\\.]*);");

    //正则 类名
    private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s+");

    @Override
    public Class<?> compile(String code, ClassLoader classLoader) {
        code = code.trim();
        //获得包名
        Matcher matcher = PACKAGE_PATTERN.matcher(code);
        String pkg;
        if (matcher.find()) {
            pkg = matcher.group(1);
        } else {
            pkg = "";
        }
        //获得类名
        matcher = CLASS_PATTERN.matcher(code);
        String cls;
        if (matcher.find()) {
            cls = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No such class name in " + code);
        }
        //获得完成类名
        String className = pkg != null && pkg.length() > 0 ? pkg + "." + cls : cls;
        try {
            //加载成功，说明已经存在
            return Class.forName(className, true, org.apache.dubbo.common.utils.ClassUtils.getCallerClassLoader(getClass()));
        } catch (ClassNotFoundException e) {
            if (!code.endsWith("}")) {
                //代码格式不正确
                throw new IllegalStateException("The java code not endsWith \"}\", code: \n" + code + "\n");
            }
            try {
                //编译代码
                return doCompile(className, code);
            } catch (RuntimeException t) {
                throw t;
            } catch (Throwable t) {
                throw new IllegalStateException("Failed to compile class, cause: " + t.getMessage() + ", class: " + className + ", code: \n" + code + "\n, stack: " + ClassUtils.toString(t));
            }
        }
    }

    /**
     * 编译代码
     * @param name 完整类名
     * @param source 代码
     * @return 编译生成的class
     * @throws Throwable
     */
    protected abstract Class<?> doCompile(String name, String source) throws Throwable;

}
