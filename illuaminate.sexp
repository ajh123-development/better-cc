; -*- mode: Lisp;-*-

(sources
  /docs/events/
  /docs/guides/
  /docs/stub/
  /docs/luaFabricJavadoc/
  /docs/luaForgeJavadoc/
  /docs/web/mount)


(doc
  (destination build/docs/lua)
  (index docs/index.md)

  (site
    (title "Better CC")
    (logo docs/logo.png)
    (url http://samhub.atwebpages.com/)
    (source-link https://github.com/ajh123-development/better-cc/blob/${commit}/${path}#L${line})

    (styles docs/web/styles.css)
    (scripts build/rollup/index.js)
    (head docs/head.html))

  (module-kinds
    (peripheral Peripherals)
    (generic_peripheral "Generic Peripherals")
    (event Events)
    (guide Guides))

  (library-path
    /docs/stub/
    /docs/luaFabricJavadoc/
    /docs/luaForgeJavadoc/))

(at /
  (linters
    syntax:string-index

    ;; It'd be nice to avoid this, but right now there's a lot of instances of
    ;; it.
    -var:set-loop

    ;; It's useful to name arguments for documentation, so we allow this. It'd
    ;; be good to find a compromise in the future, but this works for now.
    -var:unused-arg)

  (lint
    (bracket-spaces
      (call no-space)
      (function-args no-space)
      (parens no-space)
      (table space)
      (index no-space))

    (allow-clarifying-parens true)

    (globals
      :max
      _CC_DEFAULT_SETTINGS
      _CC_DISABLE_LUA51_FEATURES
      _HOST
      ;; Ideally we'd pick these up from bios.lua, but illuaminate currently
      ;; isn't smart enough.
    sleep write printError read rs)))

(at /docs/web/mount/expr_template.lua (lint (globals :max __expr__)))
