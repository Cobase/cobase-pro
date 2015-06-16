module.exports = function(grunt) {
  grunt.initConfig({
    react: {
      jsx: {
        files: [
          {
            expand: true,
            cwd: 'app/assets/js/react',
            src: [ '*.jsx' ],
            dest: 'public/dist/js/react',
            ext: '.js'
          }
        ]
      }
    },

    watch: {
      react: {
        files: 'app/assets/js/react/*.jsx',
        tasks: ['react']
      }
    }

  });

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-react');
  grunt.loadNpmTasks('grunt-browserify');

  grunt.registerTask('default', ['react']);

};

