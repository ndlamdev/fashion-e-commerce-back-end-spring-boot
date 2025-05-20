/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:03 PM-12/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc;

import java.io.File;
import java.util.List;

public interface IFileSearchGrpcClient {
	List<String> searchFile(File file);
}
