/*
 * check-in
 * Copyright (c) 2023 Scalable Solutions SRL
 *
 * Author: Marius Gligor <marius.gligor@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111, USA.
 */
package scalable.solutions.check.example;

import scalable.solutions.check.AllowedCallers;

public class Example {
    @AllowedCallers({"scalable.solutions.check.TestCallers.happy"})
    public String test() {
        System.out.println("test()");
        return "OK";
    }

    @AllowedCallers({"scalable.solutions.check.example.Example.main"})
    public int test(int param) {
        System.out.println("test(int)");
        return param;
    }

    public static void main(String[] args) {
        new Example().test(2);
        new Example().test();
    }
}
