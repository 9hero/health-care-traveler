const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/api/*",
    createProxyMiddleware({
      target: "http://localhost/",
      // target: "http://3.39.185.254:8080/",
      changeOrigin: true,
    })
  );
};
