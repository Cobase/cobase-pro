module.exports = function(grunt) {
  grunt.initConfig({
    react: {
      single_file_output: {
        files: {
          'public/dist/js/react/tweets.js': 'app/assets/js/react/tweets.jsx'
        }
      }
    },
    jshint: {
      files: ['Gruntfile.js', 'app/assets/js/**/*.js'],
      options: {
        globals: {
          jQuery: true
        }
      }
    },
    watch: {
      files: ['<%= jshint.files %>'],
      tasks: ['jshint']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-react');
  grunt.loadNpmTasks('grunt-browserify');

  grunt.registerTask('default', ['jshint', 'react']);

};

