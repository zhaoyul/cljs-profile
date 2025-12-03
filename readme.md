# Performance Profiling

To run the performance profile:

1. Ensure dependencies are installed: `npm install`
2. Compile and run the profile:
   ```bash
   npx shadow-cljs compile profile
   node out/profile.js
   ```




The profiling results will be printed to the console, showing execution time for `component-css` and other key functions.
