package com.jph.takephoto.permission;

import com.jph.takephoto.model.InvokeParam;

/**
 * Create by Sprout at 2017/7/17
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
