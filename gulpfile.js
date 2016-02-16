var gulp = require('gulp');
var sass = require('gulp-sass');
var csso = require('gulp-csso');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var plumber = require('gulp-plumber');
var templateCache = require('gulp-angular-templatecache');
var ngAnnotate = require('gulp-ng-annotate');
var jshint = require('gulp-jshint');

gulp.task('sass', function() {
    gulp.src('src/main/resources/app/stylesheets/style.scss')
        .pipe(plumber())
        .pipe(sass())
        .pipe(csso())
        .pipe(gulp.dest('src/main/resources/app/stylesheets'));
});

gulp.task('compress', function() {
    gulp.src([
            'src/main/resources/app/app.js',
            'src/main/resources/app/scripts/services/*.js',
            'src/main/resources/app/scripts/controllers/*.js',
            'src/main/resources/app/scripts/filters/*.js',
            'src/main/resources/app/scripts/directives/*.js'
        ])
        .pipe(concat('app.min.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest('src/main/resources/app/scripts'));
});

gulp.task('templates', function() {
    gulp.src('src/main/resources/app/views/**/*.html')
        .pipe(templateCache({ root: 'views', module: 'MyApp' }))
        .pipe(gulp.dest('src/main/resources/app'));
});

gulp.task('watch', function() {
    gulp.watch('src/main/resources/app/stylesheets/*.scss', ['sass']);
    gulp.watch('src/main/resources/app/views/**/*.html', ['templates']);
    gulp.watch(['src/main/resources/app/**/*.js', '!src/main/resources/app/app.min.js', '!src/main/resources/app/templates.js'], ['compress']);
});

gulp.task('jshint', function() {
    gulp.src([
            'src/main/resources/app/app.js',
            'src/main/resources/app/controllers/*.js',
            'src/main/resources/app/services/*.js',
            'src/main/resources/app/filters/*.js',
            'src/main/resources/app/directives/*.js'
        ])
        .pipe(jshint())
        //.pipe(jshint.reporter('jshint-stylish'));
        .pipe(jshint.reporter('default'));
});

gulp.task('default', ['sass','compress','templates', 'watch']);
