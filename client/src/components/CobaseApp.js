import { PropTypes } from 'react';

import 'font-awesome/css/font-awesome.css';
import 'normalize.css/normalize.css';
import '../styles/main.less';

const CobaseApp = ({ children }) => children;

CobaseApp.propTypes = {
  children: PropTypes.element.isRequired,
};

export default CobaseApp;
