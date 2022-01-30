; -*- mode: Lisp;-*-

(sources
  /docs/other/events/
  /docs/other/guides/
  /docs/other/stub/
  /docs-build/luaFabricJavadoc/
  /docs-build/luaForgeJavadoc/
  /docs/web/mount)


(doc
  (destination docs-build/docs/lua)
  (index docs/other/index.md)

  (site
    (title "Better CC")
    (logo docs/other/logo.png)
    (url http://samhub.atwebpages.com/1.18/docs/lua/)
    (source-link https://github.com/ajh123-development/better-cc/blob/${commit}/${path}#L${line})

    (styles docs/web/styles.css)
    (scripts docs-build/rollup/index.js)
    (head docs/other/head.html))

  (module-kinds
    (peripheral Peripherals)
    (generic_peripheral "Generic Peripherals")
    (event Events)
    (guide Guides))

  (library-path
    /docs/other/stub/
    /docs-build/luaFabricJavadoc/
    /docs-build/luaForgeJavadoc/))

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
