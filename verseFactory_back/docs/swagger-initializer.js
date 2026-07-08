window.onload = () => {
  const addTagsPlugin = () => {
    return {
      statePlugins: {
        spec: {
          wrapActions: {
            updateJsonSpec: (oriUpdateJsonSpec) => (spec) => {
              const paths = spec.paths;
              Object.keys(paths).forEach(path => {
                const methods = paths[path];
                const segments = path.split("/").filter(segment => segment !== "");
                const tag = segments.length > 0 ? `/${segments[0]}` : path;
                Object.keys(methods).forEach(method => {
                  const operation = methods[method];
                  if (typeof operation === "object" && operation !== null) {
                    operation.tags = operation.tags ?? [];
                    if (!operation.tags.includes(tag)) operation.tags.push(tag);
                  }
                })
              })
              return oriUpdateJsonSpec(spec);
            }
          }
        }
      }
    }
  }

  const loadSwaggerUI = (url) => {
    window.ui = SwaggerUIBundle({
      url: url,
      dom_id: '#swagger-ui',
      deepLinking: true,
      presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIStandalonePreset
      ],
      plugins: [
        SwaggerUIBundle.plugins.DownloadUrl,
        addTagsPlugin
      ],
      layout: "BaseLayout"
    });
  }

  document.querySelectorAll(".api-choice-btn").forEach(button => {
    button.addEventListener("click", () => {
      document.querySelectorAll(".api-choice-btn").forEach(btn => btn.classList.remove("active"));
      button.classList.add("active");
      loadSwaggerUI(button.getAttribute("data-spec"));
    });
  });

  loadSwaggerUI(document.querySelector(".api-choice-btn.active").getAttribute("data-spec"));
};
