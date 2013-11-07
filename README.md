# blackwater

A Clojure library for logging SQL queries and the time they took for Korma and clojure.java.jdbc

!["example image of blackwater output"](screenie.png)

## Marginalia Documentation

[Marginalia documentation](https://rawgithub.com/bitemyapp/blackwater/master/docs/uberdoc.html)

## Usage

In Leiningen add:

!["Leiningen version"](https://clojars.org/blackwater/latest-version.svg)

to your :dependencies.

Then, in your ns form:

    [black.water.korma :refer [decorate-korma!]]
    ;; or if you use cjj
    [black.water.jdbc :refer [decorate-cjj!]]

And invoke decorate-korma! or decorate-cjj! as appropriate. Thanks to technomancy's robert.hooke these operations are idempotent, but still marked with a `!` because they *ARE* modifying c.j.j and Korma.

To disable the ANSI coloring, set `*use-ansi*` in the clansi ns to false.

To customize ANSI coloring, set `time-color` or `sql-color` in the black.water ns. By default they are `:red` and `:green` respectively, check clansi for valid color symbols.

Want custom logging behavior? Kinda getting outside the scope of this library (Korma and c.j.j have more typical logging facilities) but if you must:

    [black.water.log :refer [log-fn set-logger!]]
    (set-logger! (fn [sql millis] (println sql millis "WHOOOO")))

## Development

You need to init a sqlite test database named `test.sql` at the top level of the project.

    sqlite3 test.sql
    CREATE TABLE test_table(id INTEGER PRIMARY KEY ASC, num, str);

Now your `lein test` should pass and you're ready to develop.

## Warning

Note that the Korma functionality/logging is more likely to be comprehensive than c.j.j's because it has a cleaner and more uniform API. If you think this library is failing to log something for c.j.j, it probably *is* and I would request that you file a Github issue with bitemyapp/blackwater.

## Changelog

0.0.9 - Added support for DDL and db-do-commands in general, updated JDBC dependency.

## License

Copyright © 2013 Chris Allen

Distributed under the Eclipse Public License either version 1.0 or any later version.
