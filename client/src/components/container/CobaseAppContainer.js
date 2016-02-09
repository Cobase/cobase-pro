import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import CobaseApp from '../CobaseApp';

import * as userActions from '../../actions/UserActions';

export default connect(
  state => ({
    user: state.user
  }),
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch)
  })
)(CobaseApp);
